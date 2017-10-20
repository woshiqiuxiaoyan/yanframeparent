package typeheadler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;


/**
 * BigDecimal to BigInteger type handler
 *
 * @author vincent_tech
 * @date 2017/1/24
 */
public class BigIntegerTypeHandler extends BaseTypeHandler<BigInteger> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, BigInteger parameter, JdbcType jdbcType) throws SQLException {
    ps.setBigDecimal(i, new BigDecimal(parameter));
  }

  @Override
  public BigInteger getNullableResult(ResultSet rs, String columnName) throws SQLException {
    if ( rs.getObject(columnName) == null ) {
      return null;
    }
    return rs.getBigDecimal(columnName).toBigInteger();
  }

  @Override
  public BigInteger getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    if ( cs.wasNull() ) {
      return null;
    }

    return cs.getBigDecimal(columnIndex).toBigInteger();
  }

  @Override
  public BigInteger getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    if ( rs.wasNull() ) {
      return null;
    }

    return rs.getBigDecimal(columnIndex).toBigInteger();
  }
}
