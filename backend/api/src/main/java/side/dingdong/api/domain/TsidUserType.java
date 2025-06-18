package side.dingdong.api.domain;

import com.github.f4b6a3.tsid.Tsid;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.EnhancedUserType;

public class TsidUserType implements EnhancedUserType<Tsid> {

    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

    @Override
    public Class<Tsid> returnedClass() {
        return Tsid.class;
    }

    @Override
    public boolean equals(Tsid x, Tsid y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Tsid x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public Tsid nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        long tsidLong = rs.getLong(position);
        return rs.wasNull() ? null : Tsid.from(tsidLong);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Tsid value, int index, SharedSessionContractImplementor session)
            throws SQLException {
        if (value == null) {
            st.setNull(index, Types.BIGINT);
        } else {
            st.setLong(index, value.toLong());
        }
    }

    @Override
    public Tsid deepCopy(Tsid value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Tsid value) {
        return value;
    }

    @Override
    public Tsid assemble(Serializable cached, Object owner) {
        return (Tsid) cached;
    }

    @Override
    public Tsid replace(Tsid detached, Tsid managed, Object owner) {
        return detached;
    }

    @Override
    public String toSqlLiteral(Tsid value) {
        return toString(value);
    }

    @Override
    public String toString(Tsid value) throws HibernateException {
        return value == null ? null : String.valueOf(value.toLong());
    }

    @Override
    public Tsid fromStringValue(CharSequence sequence) throws HibernateException {
        return sequence == null ? null : Tsid.from(sequence.toString());
    }
}
