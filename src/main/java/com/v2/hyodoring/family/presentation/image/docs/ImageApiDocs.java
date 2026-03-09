package com.v2.hyodoring.family.presentation.image.docs;

import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.family.core.image.ImageType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Image", description = "이미지 관련 API")
public interface ImageApiDocs {

    @Operation(
            summary = "S3 Presigned URL 발급",
            description = """
                    ### S3 Presigned URL을 발급합니다.
                    Presigned URL을 통해 이미지를 업로드할 때는 반드시 Content-Type 헤더를 포함해주세요.
                    """
    )
    ResponseEntity<CustomResponse<String>> getPresignedUrl(ImageType type, String extension);
}
