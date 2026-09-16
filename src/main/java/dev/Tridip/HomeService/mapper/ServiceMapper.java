package dev.Tridip.HomeService.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.Tridip.HomeService.dto.ServiceDto;
import dev.Tridip.HomeService.model.HomeService;

public class ServiceMapper {

    public static Object[] createMapper(ServiceDto req) {
        return new Object[] {
            req.getName(),
            req.getDescription(),
            req.getPrice(),
            req.getProviderId()
        };
    }

    public static Object[] updateMapper(ServiceDto req, Long id) {
        return new Object[] {
            req.getName(),
            req.getDescription(),
            req.getPrice(),
            id
        };
    }

    public static List<HomeService> getMappedData(ResultSet rs) throws SQLException {
        List<HomeService> list = new ArrayList<>();
        while (rs.next()) {
            HomeService service = new HomeService(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getLong("provider_id"),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
            );
            list.add(service);
        }
        return list;
    }
}
