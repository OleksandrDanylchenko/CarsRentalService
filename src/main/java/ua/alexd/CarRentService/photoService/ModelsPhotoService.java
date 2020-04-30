package ua.alexd.CarRentService.photoService;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Lazy
public class ModelsPhotoService {
    private final List<String> validExtensions = List.of("png", "jpg", "jpeg", "bmp");
    private final File modelsPhotosFolder = new File("frontend/src/assets/modelsPhotos");

    public String saveUploadingImage(@NotNull MultipartFile image) throws IOException, IllegalArgumentException {
        var uploadFilename = image.getOriginalFilename();
        if (isUploadImageValid(uploadFilename)) {
            if (!modelsPhotosFolder.exists())
                //noinspection ResultOfMethodCallIgnored
                modelsPhotosFolder.mkdir();
            var newFilename = createNewFilename(uploadFilename);
            var fullPathFile = new File(createFileFullPath(newFilename));
            image.transferTo(fullPathFile);
            return newFilename;
        } else
            throw new IllegalArgumentException();
    }

    private boolean isUploadImageValid(String uploadFilename) {
        var uploadExtension = FilenameUtils.getExtension(uploadFilename);
        return uploadFilename != null && uploadFilename.length() >= 5 &&
                validExtensions.contains(uploadExtension);
    }

    private @NotNull String createNewFilename(String uploadFilename) {
        var uuidStr = UUID.randomUUID().toString();
        return uuidStr + "__" + uploadFilename;
    }

    private @NotNull String createFileFullPath(String filename) throws IOException {
        return modelsPhotosFolder.getCanonicalPath() + '\\' + filename;
    }

    public void deletePhoto(String imageName) throws IOException {
        var fullFilepath = createFileFullPath(imageName);
        var deletionFile = new File(fullFilepath);
        //noinspection ResultOfMethodCallIgnored
        deletionFile.delete();
    }
}