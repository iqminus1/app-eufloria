package up.pdp.appeufloria.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import up.pdp.appeufloria.utils.AppConstants;

@RequestMapping(AppConstants.APP_V1 + "/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

}
