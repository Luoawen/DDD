package cn.m2c.scm.port.adapter.restful.admin.special;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.special.GoodsSpecialApplication;
import cn.m2c.scm.application.special.command.GoodsSpecialImageCommand;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;

/**
 * 特惠价图片 
 */
@RestController
@RequestMapping("/admin/goods/special")
public class AdminGoodsSpecial {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminGoodsSpecial.class);
	
	@Autowired
	GoodsSpecialApplication goodsSpecialApplication;
	
	/**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getGoodsSpecialImageId(){
    	MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_GOODS_SPECIAL_IMAGE_ID);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("获取GoodsSpecialImageId异常 Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
	
	/**
	 * 保存特惠价图片
	 * @param goodsSpecialImage
	 * @return
	 */
	@RequestMapping(value = "/image" ,method = RequestMethod.PUT)
	public ResponseEntity<MResult> saveGoodsSpecialImage(
		@RequestParam(value="imageId",required = false) String imageId,
		@RequestParam(value = "specialImageUrl", required = false) String specialImageUrl
			){
		MResult result = new MResult(MCode.V_1);
		try {
			GoodsSpecialImageCommand goodsSpecialImageCommand = new GoodsSpecialImageCommand(imageId, specialImageUrl);
			goodsSpecialApplication.saveGoodsSpecialImage(goodsSpecialImageCommand);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("saveGoodsSpecialImage NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("saveGoodsSpecialImage Exception e:", e);
			result = new MResult(MCode.V_400, "保存特惠价图片失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
}
