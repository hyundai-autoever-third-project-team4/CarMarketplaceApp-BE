package store.carjava.marketplace.app.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import store.carjava.marketplace.common.util.image.ImageUploader;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageUploadExampleController {
    private final ImageUploader imageUploader;

    @PostMapping("/test")
    public List<String> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files) throws IOException {
        return imageUploader.uploadMultiFiles(files, "reviews");
    }
}
