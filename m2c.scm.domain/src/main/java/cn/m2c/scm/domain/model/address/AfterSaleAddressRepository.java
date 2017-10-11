package cn.m2c.scm.domain.model.address;

/**
 * 售后地址
 */
public interface AfterSaleAddressRepository {
    /**
     * 获取售后地址
     *
     * @param addressId
     * @return
     */
    public AfterSaleAddress getAfterSaleAddressByAddressId(String addressId);

    /**
     * 保存售后地址
     *
     * @param afterSaleAddress
     */
    public void save(AfterSaleAddress afterSaleAddress);
}
