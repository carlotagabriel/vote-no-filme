package carlota.components.database.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class DefaultModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "deleted", columnDefinition = "boolean default false")
	private boolean deleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts_inserted", updatable = false, insertable = false, columnDefinition = "timestamp without time zone default NOW()")
    private Date ts_inserted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTs_inserted() {
		return ts_inserted;
	}
	
	public void setTs_inserted(Date ts_inserted) {
		this.ts_inserted = ts_inserted;
	}
	
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
