package uz.pdp.appeufloria.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.appeufloria.payload.ApiResultDTO;

public interface AttachmentService {
    void read(HttpServletResponse resp,Integer id);
    ApiResultDTO<?> create(HttpServletRequest req);
    ApiResultDTO<?> update(HttpServletRequest req,Integer id);
    void delete(Integer id);
}
