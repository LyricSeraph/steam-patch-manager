package top.lyriclaw.spm.db.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes({JdbcType.OTHER})
@MappedTypes({InetAddress.class})
public class IpTypeHandler extends BaseTypeHandler<InetAddress> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InetAddress parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getHostAddress(), jdbcType.TYPE_CODE);
    }

    @Override
    public InetAddress getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName, InetAddress.class);
    }

    @Override
    public InetAddress getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getObject(columnIndex, InetAddress.class);
    }

    @Override
    public InetAddress getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getObject(columnIndex, InetAddress.class);
    }
}
