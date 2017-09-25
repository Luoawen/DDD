package cn.m2c.scm.application.goods.goods;

import cn.m2c.common.MCode;
import cn.m2c.goods.domain.dealer.Dealer;
import cn.m2c.goods.domain.dealer.DealerRepository;
import cn.m2c.goods.domain.goods.Goods;
import cn.m2c.goods.domain.goods.GoodsRepository;
import cn.m2c.goods.domain.seller.Seller;
import cn.m2c.goods.domain.seller.SellerRepository;
import cn.m2c.goods.domain.seller.StaffReportCount;
import cn.m2c.goods.domain.seller.StaffReportCountRepository;
import cn.m2c.goods.exception.NegativeCode;
import cn.m2c.goods.exception.NegativeException;
import cn.m2c.scm.application.goods.goods.command.StaffAddOrUpdateCmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SellerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerApplication.class);

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private StaffReportCountRepository reportRepository;

    @Autowired
    GoodsRepository godsRepository;

    @Autowired
    DealerRepository dealerRepository;


    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addStaff(StaffAddOrUpdateCmd cmd, String staffId) throws NegativeException {
        LOGGER.info("staff add command >>{}", cmd.toString());
        Seller staff = sellerRepository.getSeller(staffId);
        if (staff != null)
            throw new NegativeException(NegativeCode.SELLER_IS_EXIST, "业务员已存在.");
        staff = new Seller(staffId, cmd.getUserId(), cmd.getAccNo(), cmd.getStaffName(), cmd.getStaffPhone(),
                cmd.getMail(), cmd.getSex(), cmd.getAge(), cmd.getProCode(), cmd.getCityCode(), cmd.getAreaCode(), cmd.getProName(), cmd.getCityName(), cmd.getAreaName(), cmd.getRegisDate(), 0);
        sellerRepository.save(staff);
    }


    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addDealerNum(String dealerId, String staffId, Long regisDate) throws NegativeException {
        LOGGER.info("staff add dealer num >>{}", dealerId, staffId, regisDate);
        LOGGER.info("**********************************dealerId" + dealerId + "***********staffId***" + staffId + "************regisDate****" + regisDate);
        StaffReportCount staffReport = new StaffReportCount(staffId, 1, 1, new Date(regisDate));
        reportRepository.save(staffReport);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsNum(String goodsId, String staffId, Long regisDate) throws NegativeException {
        LOGGER.info("staff add goods num >>{}", goodsId, staffId, regisDate);
        StaffReportCount staffReport = new StaffReportCount(staffId, 2, 1, new Date(regisDate));
        reportRepository.save(staffReport);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addOrderNum(String goodsId, Long orderDate) throws NegativeException {
        LOGGER.info("staff add order num >>{}", goodsId);
        Goods goods = godsRepository.getGoodsDetail(goodsId);
        if (null == goods)
            throw new NegativeException(MCode.V_1, "商品不存在.");
        Dealer dealer = dealerRepository.getDealerDetail(goods.getDealerId());
        if (dealer == null)
            throw new NegativeException(MCode.V_1, "经销商不存在.");
        System.out.println("----------------供应商id" + dealer.getDealerId());
        String sysuserId = dealer.getSellerId();
        LOGGER.info("---------------------sysuserId:" + sysuserId);
        if (sysuserId != null && !"".equals(sysuserId)) {
            Seller staff = sellerRepository.getSysSeller(sysuserId);
            if (null == staff)
                throw new NegativeException(MCode.V_1, "业务员不存在");
            staff.addOrderNum();
            sellerRepository.save(staff);
            StaffReportCount staffReport = new StaffReportCount(sysuserId, 3, 1, new Date(orderDate));
            reportRepository.save(staffReport);
        }


    }

    /**
     * 更新经销商业务员
     * @param cmd
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void updateStaff(StaffAddOrUpdateCmd cmd) throws NegativeException {
        LOGGER.info("staff update command >>{}", cmd.toString());
        Seller staff = sellerRepository.getSysSeller(cmd.getUserId());
        if (null == staff) {
            throw new NegativeException(NegativeCode.SELLER_IS_EXIST, "经销商业务员不存在");
        }

        staff.update(cmd.getAccNo(), cmd.getStaffName(), cmd.getStaffPhone(), cmd.getMail(),
                cmd.getSex(), cmd.getAge(), cmd.getProCode(), cmd.getCityCode(), cmd.getAreaCode(), cmd.getProName(),
                cmd.getCityName(), cmd.getAreaName());

        sellerRepository.save(staff);
    }
}
