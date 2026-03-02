package com.v2.hyodoring.account.presentation.account;

import com.v2.hyodoring.account.application.account.service.AccountApiQueryService;
import com.v2.hyodoring.account.presentation.account.docs.AccountApiDocs;
import com.v2.hyodoring.account.presentation.account.domain.AccountProfileResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController implements AccountApiDocs {

    private final AccountApiQueryService accountApiQueryService;

    @GetMapping
    public ResponseEntity<CustomResponse<AccountProfileResponse>> getProfile() {
        Long accountId = 1L; // TODO: 인증된 사용자 ID로 변경
        return CustomResponse.onSuccess(BaseSuccessResponse.OK, accountApiQueryService.getProfile(accountId));
    }
}
