package com.lvym.gateway.mapper;


import com.lvym.gateway.entity.GateWayEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

public interface AntsGatewayMapper {
    @Select("SELECT ID AS ID, route_id as routeid, route_name as routeName,route_pattern as routePattern\n" +
            ",route_type as routeType,route_url as routeUrl\n" +
            " FROM ants_gateway\n")
    List<GateWayEntity> gateWayAll();

    @Update("update ants_gateway set route_url=#{routeUrl} where route_id=#{routeId};")
    Integer updateGateWay(@Param("routeId") String routeId, @Param("routeUrl") String routeUrl);

}
