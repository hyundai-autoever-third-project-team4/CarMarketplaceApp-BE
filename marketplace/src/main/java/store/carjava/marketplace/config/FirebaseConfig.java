package store.carjava.marketplace.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import store.carjava.marketplace.firebase.exception.FirebaseInitializeFailed;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("classpath:firebase/chajava-firebase-admin.json")
    private Resource firebaseAdmin;

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount = firebaseAdmin.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("Firebase Admin SDK 초기화 성공");
        } catch (Exception e) {
            System.err.println("Firebase Admin SDK 초기화 실패: " + e.getMessage());
            throw new FirebaseInitializeFailed();
        }
    }
}

