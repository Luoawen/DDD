package cn.m2c.scm.port.adapter.restful.web.address;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.address.AfterSaleAddressApplication;
import cn.m2c.scm.application.address.command.AfterSaleAddressCommand;
import cn.m2c.scm.application.address.data.bean.AfterSaleAddressBean;
import cn.m2c.scm.application.address.data.representation.AfterSaleAddressRepresentation;
import cn.m2c.scm.application.address.query.AfterSaleAddressQueryApplication;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 售后地址
 *
 * @author ps
 */
@RestController
@RequestMapping("/after/sale/address")
public class AfterSaleAddressAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(AfterSaleAddressAgent.class);

    @Autowired
    AfterSaleAddressApplication afterSaleAddressApplication;
    @Autowired
    AfterSaleAddressQueryApplication afterSaleAddressQueryApplication;

    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getAfterSaleAddressId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_AFTER_SALE_ADDRESS_PREFIX_TITLE);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getAfterSaleAddressId Exception e:", e);
            result = new MResult(MCode.V_400, "获取售后地址ID失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 增加售后地址
     *
     * @param dealerId      经销商id
     * @param addressId     售后地址id
     * @param proCode       省编码
     * @param cityCode      市编码
     * @param areaCode      区编码
     * @param proName       省名称
     * @param cityName      市名称
     * @param areaName      区名称
     * @param address       详细地址
     * @param contactName   联系人名称
     * @param contactNumber 联系电话
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MResult> addAfterSaleAddress(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "addressId", required = false) String addressId,
            @RequestParam(value = "proCode", required = false) String proCode,
            @RequestParam(value = "cityCode", required = false) String cityCode,
            @RequestParam(value = "areaCode", required = false) String areaCode,
            @RequestParam(value = "proName", required = false) String proName,
            @RequestParam(value = "cityName", required = false) String cityName,
            @RequestParam(value = "areaName", required = false) String areaName,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "contactName", required = false) String contactName,
            @RequestParam(value = "contactNumber", required = false) String contactNumber) {
        MResult result = new MResult(MCode.V_1);
        try {
            AfterSaleAddressCommand command = new AfterSaleAddressCommand(addressId, dealerId, proCode, cityCode, areaCode,
                    proName, cityName, areaName, address, contactName, contactNumber);
            afterSaleAddressApplication.addAfterSaleAddress(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addAfterSaleAddress NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addAfterSaleAddress Exception e:", e);
            result = new MResult(MCode.V_400, "添加售后地址失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 修改售后地址
     *
     * @param dealerId      经销商id
     * @param addressId     售后地址id
     * @param proCode       省编码
     * @param cityCode      市编码
     * @param areaCode      区编码
     * @param proName       省名称
     * @param cityName      市名称
     * @param areaName      区名称
     * @param address       详细地址
     * @param contactName   联系人名称
     * @param contactNumber 联系电话
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyAfterSaleAddress(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "addressId", required = false) String addressId,
            @RequestParam(value = "proCode", required = false) String proCode,
            @RequestParam(value = "cityCode", required = false) String cityCode,
            @RequestParam(value = "areaCode", required = false) String areaCode,
            @RequestParam(value = "proName", required = false) String proName,
            @RequestParam(value = "cityName", required = false) String cityName,
            @RequestParam(value = "areaName", required = false) String areaName,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "contactName", required = false) String contactName,
            @RequestParam(value = "contactNumber", required = false) String contactNumber) {
        MResult result = new MResult(MCode.V_1);
        try {
            AfterSaleAddressCommand command = new AfterSaleAddressCommand(addressId, dealerId, proCode, cityCode, areaCode,
                    proName, cityName, areaName, address, contactName, contactNumber);
            afterSaleAddressApplication.modifyAfterSaleAddress(command);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("modifyAfterSaleAddress Exception e:", e);
            result = new MResult(MCode.V_400, "修改售后地址失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询售后地址
     *
     * @param dealerId 经销商id
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MResult> getAfterSaleAddress(
            @RequestParam(value = "dealerId", required = false) String dealerId) {
        MResult result = new MResult(MCode.V_1);
        if (StringUtils.isEmpty(dealerId)) {
            result = new MResult(MCode.V_1, "商家ID为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        try {
            AfterSaleAddressBean bean = afterSaleAddressQueryApplication.queryAfterSaleAddressByDealerId(dealerId);
            if (null != bean) {
                result.setContent(new AfterSaleAddressRepresentation(bean));
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getAfterSaleAddress Exception e:", e);
            result = new MResult(MCode.V_400, "查询售后地址失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
