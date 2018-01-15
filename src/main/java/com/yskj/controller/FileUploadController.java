package com.yskj.controller;

import com.yskj.domain.ResponseObj;
import com.yskj.service.serviceImpl.UserServiceImpl;
import com.yskj.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 文件上传
 *
 * @author zhouchuang
 * @create 2018-01-15 22:15
 */
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {
    @Autowired
    private UserServiceImpl userService;    //自动载入Service对象
    private ResponseObj responseObj;



    @RequestMapping(value = "/uploadHeadPic"
            , method = RequestMethod.POST
            , produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object uploadHeadPic(@RequestParam(required = false) MultipartFile file, HttpSession session) {

        if (null == file || file.isEmpty()) {
            responseObj = new ResponseObj();
            responseObj.setCode(ResponseObj.FAILED);
            responseObj.setMsg("文件不能为空");
            return new GsonUtils().toJson(responseObj);
        }
        responseObj = new ResponseObj();
        responseObj.setCode(ResponseObj.OK);
        responseObj.setMsg("文件长度为：" + file.getSize());


        File tempFile = new File("E:/upload/iJob");
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }
        try {
            //获取输出流
            OutputStream os=new FileOutputStream("E:/upload/iJob/"+new Date().getTime()+file.getOriginalFilename());
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return new GsonUtils().toJson(responseObj);
    }

}
