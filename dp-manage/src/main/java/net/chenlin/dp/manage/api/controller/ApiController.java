package net.chenlin.dp.manage.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.chenlin.dp.common.constant.StatusConstant;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.exception.RRException;
import net.chenlin.dp.common.utils.*;
import net.chenlin.dp.manage.api.dto.ApiDTO;
import net.chenlin.dp.manage.api.utils.WeatherUtils;
import net.chenlin.dp.manage.program.service.ManaProgramTaskService;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;
import net.chenlin.dp.manage.terminal.serviece.ManaDevicesService;
import net.chenlin.dp.manage.terminal.serviece.ManaTerminalService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 供外部调用api接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/17 16:15
 */
@RequestMapping("/api")
@Controller
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ManaProgramTaskService manaProgramTaskService;

    @Autowired
    private ManaTerminalService manaTerminalService;

    @Autowired
    private ManaDevicesService manaDevicesService;

    /**
     * 默认token
     */
    @Value("${api.token}")
    private String myToken;

    /**
     * url 超时设定
     */
    @Value("${api.urlTimeout}")
    private Long timestampOffset;

    /**
     * 终端初始化注册
     *
     * @param apiDTO   必传参数（timestamp，signature，token） 身份认证用的
     * @param deviceId 设备的唯一标识码
     * @return
     */
    @RequestMapping(value = "/register.do")
    @ResponseBody
    public R register(ApiDTO apiDTO,
                      @RequestParam(value = "deviceId") String deviceId) {
        //权限验证
        if (!checkParam(apiDTO.getTimestamp(), apiDTO.getSignature(), apiDTO.getToken())) {
            throw new RRException("无权限操作", 403);
        }

        ManaDevicesDTO manaDevicesDTO = manaDevicesService.getDeviceByDeviceId(deviceId);
        // 判断该设备是否已经注册过
        if (CommonUtils.isNullOrEmpty(manaDevicesDTO)) {
            manaDevicesDTO = new ManaDevicesDTO();
            manaDevicesDTO.setDeviceId(deviceId);
            manaDevicesService.saveDevice(manaDevicesDTO);
        }

        return R.ok("注册成功");
    }


    /**
     * 获取节目单配置json
     *
     * @param apiDTO
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/get_task_config_json.do")
    @ResponseBody
    public JSON getTaskConfigJson(ApiDTO apiDTO, @RequestParam(value = "taskId", required = false) Long taskId) {
        if (taskId == null || !CommonUtils.isIntThanZero(taskId.intValue())) {
            throw new RRException("节目单id错误");
        }
        //权限验证
        if (!checkParam(apiDTO.getTimestamp(), apiDTO.getSignature(), apiDTO.getToken())) {
            throw new RRException("无权限操作", 403);
        }

        // 生成json字符串
        String strConfigJson = manaProgramTaskService.generateJson(taskId);
        if (StringUtils.isBlank(strConfigJson)) {
            return null;
//            throw new RRException("没有该节目单信息");
        }

        return JSONObject.parseObject(strConfigJson);
    }

    @RequestMapping(value = "/get_now_time.do")
    @ResponseBody
    public String getNowTime() {
        return DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
    }

    /**
     * 同步完成接口
     *
     * @param terminalId
     */
    @RequestMapping(value = "/sync_finish.do")
    @ResponseBody
    public String syncStatus(String terminalId) {
        /*log.info("****************************************************************");
        log.info("terminalId ============>>> :" + terminalId);
        log.info("****************************************************************");*/
        if (terminalId != null) {
            System.out.println("【同步完成】.....terminalId= " + terminalId);
            manaTerminalService.syncStatus(terminalId, StatusConstant.TerminalSyncStatusConstant.SYNC_STATUS_SUCCESS_SYNC);
        }
        return DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
    }

    /**
     * 同步失败接口
     */
    @RequestMapping(value = "/sync_failed.do")
    public String syncStatusFailed(String terminalId) {
        if (terminalId != null) {
            System.out.println("【同步失败】.....deviceId= " + terminalId);
            manaTerminalService.syncStatus(terminalId, StatusConstant.TerminalSyncStatusConstant.SYNC_STATUS_FAIL_SYNC);
        }
        return DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
    }

    /**
     * 网络状态接口
     *
     * @param terminalId
     */
    @RequestMapping(value = "/network_online.do")
    @ResponseBody
    public void networkOnline(String terminalId) {
        manaTerminalService.networkOnline(terminalId);
    }


    /**
     * 检查请求参数可靠性
     *
     * @return
     */
    public Boolean checkParam(String timestamp, String signature, String token) {
        // 三个必带参数
        if (StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(signature) || StringUtils.isEmpty(token)) {
            return false;
        } else if (!myToken.equals(token)) {  // token 不一致
            return false;
        }
        String[] arr = new String[]{timestamp, token};
        // 对数组进行排序
        String str = CommonUtils.sortArrayToString(arr);
        // 进行sha1 加密处理
        str = CommonUtils.getSha1(str);
        if (!str.equals(signature)) {   // 签名不一致
            return false;
        }
        Long timestampSmall = Long.parseLong(timestamp);       // 请求时的时间
        Long timestampMax = CommonUtils.timestamp();            // 当前时间
        Long count = CommonUtils.getBetweenMillisecondss(timestampMax, timestampSmall);
        if (count > timestampOffset) {   // 链接超时失效
            return false;
        }
        return true;
    }

    /**
     * 获取天气接口
     */
    @RequestMapping(value = "/get_now_weather.do")
    @ResponseBody
    public Map<String, String> getNowWeather(HttpServletRequest request) throws Exception {
        String IP = NetworkUtil.getIpAddress(request);
        AddressUtils addr = new AddressUtils();
        String address = addr.getAddresses(IP, "utf-8");
        String cityCode = manaTerminalService.getCityCodeByName(address);
        return WeatherUtils.getWeatherInfo(cityCode);
    }

}
