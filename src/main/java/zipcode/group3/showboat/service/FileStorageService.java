package zipcode.group3.showboat.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private Path rootLocation = Paths.get("uploads");

    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path location = rootLocation.resolve(filename);

        try {
            InputStream inputSteam = file.getInputStream();
            Files.copy(inputSteam, location, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            System.out.println("Failed to store File");
        }
        finally {
            System.out.println(location.toString());
        }

        return filename;
    }

    public String storeWithFileName(MultipartFile file, String filename) {
        Path location = rootLocation.resolve(filename);

        try {
            InputStream inputSteam = file.getInputStream();
            Files.copy(inputSteam, location, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            System.out.println("Failed to store File");
        }
        finally {
            System.out.println(location.toString());
        }

        return filename;
    }

    public Resource load(String filename) {
        try {
            Path filepath = rootLocation.resolve(filename);
            Resource resource = new UrlResource(filepath.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                return null;
            }
        }
        catch (MalformedURLException e) {
            System.out.println(e);
            return null;
        }

    }

}