package com.linkc.linkcbackend.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AzureBlobService {
    @Value("{azure.storage.connectionstring}")
    private String azureConnectionString;
    @Value("${azure.storage.containername}")
    private String azureContainerName;
    private BlobServiceClient blobServiceClient;
    private BlobContainerClient blobContainerClient;

    public AzureBlobService() {
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(azureConnectionString)
                .buildClient();

        blobContainerClient = blobServiceClient.getBlobContainerClient(azureContainerName);
    }

    public String uploadImageToBlob(String encodedImage) throws Exception{
        String fileExtension = getImageFileExtension(encodedImage);
        String fileName = java.util.UUID.randomUUID() + "." + fileExtension;
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

        byte[] bytes = Base64.getDecoder().decode(encodedImage);
        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(bytes)) {
            blobClient.upload(dataStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType("image/" + fileExtension));
        return blobClient.getBlobUrl();
    }

    private String getImageFileExtension(String encodedImage) throws Exception{
        if (encodedImage.startsWith("/")) {
            return "jpg";
        } else if (encodedImage.startsWith("i")) {
            return "png";
        } else if (encodedImage.startsWith("R")) {
            return "gif";
        } else if (encodedImage.startsWith("U")) {
            return "webp";
        }

        throw new Exception("File image type not supported");
    }
}
