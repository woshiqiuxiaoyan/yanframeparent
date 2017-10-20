package typeheadler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

/**
 * Timestamp to org.joda.time.DateTime type handler
 *
 * @author vincent_tech
 * @date 2017/1/24
 */
public class JodaDateTimeTypeHandler extends BaseTypeHandler<DateTime> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, DateTime parameter, JdbcType jdbcType) throws SQLException {
    Timestamp timestamp = Timestamp.valueOf(parameter.toString("yyyy-MM-dd HH:mm:ss.SSS"));
    ps.setTimestamp(i, timestamp);
  }

  @Override
  public DateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
//    if ( rs.wasNull() ) {
//      return null;
//    }

    Object o = rs.getObject(columnName);

    if ( o instanceof Date || o instanceof String ) {
      return new DateTime(o);
    }
    else {
      return null;
    }
  }

  @Override
  public DateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    if ( rs.wasNull() ) {
      return null;
    }

    Object o = rs.getObject(columnIndex);

    if ( o instanceof Date || o instanceof String ) {
      return new DateTime(o);
    }
    else {
      return null;
    }
  }

  @Override
  public DateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    if ( cs.wasNull() ) {
      return null;
    }

    Object o = cs.getObject(columnIndex);

    if ( o instanceof Date || o instanceof String ) {
      return new DateTime(o);
    }
    else {
      return null;
    }
  }
}
