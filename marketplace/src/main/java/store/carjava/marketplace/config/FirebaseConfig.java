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

@Configuration
public class FirebaseConfig {
    @Value("classpath:firebase/chajava-firebase-admin.json")
    private Resource firebaseAdmin;

    @PostConstruct
    public void init() {
        try{
            FileInputStream serviceAccount = new FileInputStream(firebaseAdmin.getFile());

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new FirebaseInitializeFailed();
        }
    }
}
