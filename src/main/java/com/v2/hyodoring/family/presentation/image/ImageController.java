package com.v2.hyodoring.family.presentation.image;

import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.family.application.image.service.S3ImageService;
import com.v2.hyodoring.family.core.image.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final S3ImageService s3ImageService;

    @GetMapping("/presigned-url")
    public ResponseEntity<CustomResponse<String>> getPresignedUrl(
            @RequestParam ImageType type,
            @RequestParam String extension
    ) {
        return CustomResponse.onSuccess(BaseSuccessResponse.OK,
                s3ImageService.getPresignedUrl(type, extension));
    }
}
