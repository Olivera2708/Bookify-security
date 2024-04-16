package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Image;
import rs.ac.uns.ftn.Bookify.repository.FileSystemRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IImageRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;

import java.io.IOException;
import java.util.*;

@Service
public class ImageService implements IImageService {
    @Autowired
    FileSystemRepository fileSystemRepository;
    @Autowired
    IImageRepository imageRepository;
    @Autowired
    IAccommodationRepository accommodationRepository;

    @Override
    public Image save(byte[] bytes, String subFolderName, String imageName) throws Exception {
        String location = fileSystemRepository.save(bytes, subFolderName, imageName);
        return imageRepository.save(new Image(location, imageName));
    }

    @Override
    public void delete() throws Exception {
        List<Image> images = imageRepository.findImageByIdNotInAccommodation();
        for (Image image : images) {
            fileSystemRepository.delete(image.getImagePath());
            imageRepository.delete(image);
        }
    }

    @Override
    public void deleteById(Long imageId) throws Exception {
        Accommodation accommodation = accommodationRepository.getAccommodationByImageId(imageId);
        accommodation.getImages().removeIf(image -> Objects.equals(image.getId(), imageId));
        accommodationRepository.save(accommodation);
        delete();
        imageRepository.deleteById(imageId);
    }

    public void save(Long accommodationId, List<MultipartFile> images) throws Exception {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
//        delete();
        for (MultipartFile image : images) {
            String location = fileSystemRepository.save(image.getBytes(), accommodationId.toString(), image.getName());
            Image newImage = new Image(location, image.getName());
            accommodation.getImages().add(newImage);
            imageRepository.save(newImage);
        }
        accommodationRepository.save(accommodation);
    }

    @Override
    public FileSystemResource find(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return fileSystemRepository.findInFileSystem(image.getImagePath());
    }
}
