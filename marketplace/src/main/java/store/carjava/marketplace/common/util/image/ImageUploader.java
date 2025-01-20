package store.carjava.marketplace.common.util.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Async;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageUploader {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 여러 MultipartFile 업로드 (비동기)
    @Async
    public CompletableFuture<List<String>> uploadMultiFilesAsync(List<MultipartFile> multipartFiles, String dirName) {
        long startTime = System.currentTimeMillis(); // 시작 시간
        List<CompletableFuture<String>> futures = multipartFiles.stream()
                .map(file -> uploadSingleFileAsync(file, dirName))
                .toList();
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        CompletableFuture<List<String>> result = allFutures.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
        long endTime = System.currentTimeMillis(); // 종료 시간
        log.info("uploadMultiFilesAsync 실행 시간: {} ms", (endTime - startTime));
        return result;
    }

    // 단일 MultipartFile 업로드 (비동기)
    @Async
    public CompletableFuture<String> uploadSingleFileAsync(MultipartFile multipartFile, String dirName) {
        long startTime = System.currentTimeMillis(); // 시작 시간
        try {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
            String result = upload(uploadFile, dirName);
            long endTime = System.currentTimeMillis(); // 종료 시간
            log.info("uploadSingleFileAsync 실행 시간: {} ms", (endTime - startTime));
            return CompletableFuture.completedFuture(result);
        } catch (IOException e) {
            log.error("파일 업로드 실패: {}", multipartFile.getOriginalFilename(), e);
            return CompletableFuture.completedFuture(null);
        }
    }

    // 여러 MultipartFile 업로드 (동기)
    public List<String> uploadMultiFiles(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        long startTime = System.currentTimeMillis(); // 시작 시간
        List<String> result = multipartFiles.stream()
                .map(file -> {
                    try {
                        return uploadSingleFile(file, dirName);
                    } catch (IOException e) {
                        throw new RuntimeException("파일 업로드 실패: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis(); // 종료 시간
        log.info("uploadMultiFiles 실행 시간: {} ms", (endTime - startTime));
        return result;
    }

    // 단일 MultipartFile 업로드 (동기)
    public String uploadSingleFile(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}

