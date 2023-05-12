package admin.zoowayss.top.service;

import admin.zoowayss.top.entity.Server;
import admin.zoowayss.top.mapper.ServerMapper;
import admin.zoowayss.top.service.impl.ServerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements ServerService {
    @Resource
    private ServerMapper serverMapper;
}
