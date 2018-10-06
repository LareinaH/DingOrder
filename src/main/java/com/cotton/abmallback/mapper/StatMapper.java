package com.cotton.abmallback.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatMapper {


    /* ***************************会员相关*********************************** */

    /**
     * 总用户数（包括小白用户）
     * @return
     */
    @Select("select count(*) from member where is_deleted=0")
    Long getTotalMember();


    /**
     * 总会员数(不包含小白)
     * @return
     */
    @Select("select count(*) from member where is_deleted=0 and level != 'WHITE'")
    Long getAgentMemberCount();

    /**
     * 一段时间区间的会员数目
     */
    @Select("select count(*) from member where is_deleted=0 and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd} and level != 'WHITE'")
    Long getAgentMemberCountByTime(@Param("gmtStart") Date gmtStart, @Param("gmtEnd") Date gmtEnd);

    /**
     * 复购会员数
     */
    @Select("SELECT count(*) FROM (" +
            "SELECT  member_id,count(*) as a from orders WHERE is_deleted = '0'  " +
            "and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')" +
            "GROUP BY member_id HAVING count(*) > 1 )" +
            "as aa")
    long getRepurchaseMemberCount();

    /**
     * 一段时间区间的复购会员数
     */
    @Select("SELECT count(*) FROM (" +
            "SELECT  member_id,count(*) as a from orders WHERE is_deleted = '0'" +
            "and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL') " +
            "and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd} " +
            "GROUP BY member_id HAVING count(*) > 1 )" +
            "as aa")
    long getRepurchaseMemberCountByTime(@Param("gmtStart")Date gmtStart, @Param("gmtEnd") Date gmtEnd);

    /**
     * 会员团队统计
     * @param memberId
     * @return
     */
    @Select("select level,count(*) as count  from member WHERE referrer_id = #{id} and is_deleted=0 GROUP BY level")
    List<Map<String,Object>> getMemberTeamCountGroupByLevel(@Param("id") Long memberId);


    /* ***************************订单相关*********************************** */


    /**
     * 总订单数
     * @return
     */
    @Select("select count(*) from orders where is_deleted=0 and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')")
    Long getTotalOrders();

    /**
     * 一段时间的订单数
     */
    @Select("select count(*) from orders" +
            " where is_deleted=0" +
            " and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')" +
            " and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd}")
    Long getOrdersCountByTime(@Param("gmtStart") Date gmtStart, @Param("gmtEnd") Date gmtEnd);

    @Select("select count(*) from orders" +
            " where is_deleted=0" +
            " and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')" +
            " and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd}" +
            " and pay_mode=#{payMode}")
    Long getOrdersCountByTimeByPayMode(@Param("gmtStart") Date gmtStart, @Param("gmtEnd") Date gmtEnd, @Param("payMode") String payMode);

    /**
     * 订单状态统计
     * @param gmtStart
     * @param gmtEnd
     * @return
     */
    @Select("select order_status as orderStatus, count(*) as sum from orders " +
            "where is_deleted=0 and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd}" +
            "group by order_status order by sum desc")
    List<Map<String, Long>> getOrderStatusStats(@Param("gmtStart") String gmtStart, @Param("gmtEnd") String gmtEnd);



    //总购物额度
    @Select("select sum(total_money) from orders where is_deleted=0 and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL');")
    BigDecimal getOrderMoney();


    @Select("SELECT a.id , sum(a.total_money) as total_money ,sum(a.rebate_money) as rebate_money, count(*) as order_count, b.good_name,b.goods_specification_name,b.good_specification_id as good_specification_id ,b.goods_specification_no,b.good_price from (" +
            "SELECT id, total_money ,rebate_money FROM orders WHERE ( is_deleted = '0' and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL') and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd} ) )as a " +
            "left JOIN order_goods as b on (a.id = b.order_id)" +
            "GROUP BY good_specification_id ORDER BY ${sortKey} ${sortOrder}  LIMIT #{start},#{pageSize}")
    List<Map<String, Object>> ordersRank(@Param( "start") int start, @Param( "pageSize") int pageSize, @Param("gmtStart") String gmtStart, @Param("gmtEnd") String gmtEnd, @Param("sortKey") String sortKey,@Param("sortOrder") String sortOrder);


    @Select("SELECT count(*) from (SELECT good_specification_id FROM (" +
            "SELECT id, total_money ,rebate_money FROM orders WHERE (is_deleted = '0'  and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL') and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd} ))as a " +
            "left JOIN order_goods as b on (a.id = b.order_id) " +
            "GROUP BY good_specification_id) as d")
    long countOrdersRank(@Param("gmtStart") String gmtStart, @Param("gmtEnd") String gmtEnd);

    @Select("select COALESCE(sum(total_money), 0) from orders" +
            " where is_deleted=0 and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd}" +
            " and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')")
    BigDecimal getTotalSaleMoney(@Param("gmtStart") Date gmtStart, @Param("gmtEnd") Date gmtEnd);

    @Select("select COALESCE(sum(rebate_money), 0) from orders" +
            " where is_deleted=0 and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd}" +
            " and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')")
    BigDecimal getTotalRebateMoney(@Param("gmtStart") Date gmtStart, @Param("gmtEnd") Date gmtEnd);

    @Select("select COALESCE(sum(total_money), 0) from orders" +
            " where is_deleted=0 and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd}" +
            " and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')" +
            " and pay_mode=#{payMode}")
    BigDecimal getTotalSaleMoneyByPayMode(@Param("gmtStart") Date gmtStart, @Param("gmtEnd") Date gmtEnd, @Param("payMode") String payMode);

    @Select("select DATE_FORMAT(gmt_create, '%Y-%m-%d') as histDay, sum(total_money) as totalMoney from orders" +
            " where is_deleted=0 and gmt_create >= #{gmtStart} and gmt_create <= #{gmtEnd}" +
            " and order_status not in ('WAIT_BUYER_PAY','CANCEL','SYSTEM_CANCEL')" +
            " group by DATE_FORMAT(gmt_create, '%Y-%m-%d')" +
            " order by gmt_create")
    List<Map<String, BigDecimal>> getSalesMoneyTrend(@Param("gmtStart") String gmtStart, @Param("gmtEnd") String gmtEnd);
}
