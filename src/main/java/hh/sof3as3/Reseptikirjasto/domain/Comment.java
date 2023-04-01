package hh.sof3as3.Reseptikirjasto.domain;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comment {
	
	// attribuutit
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // autogeneroidaan id
	private Long id;
	private String message;
	private Timestamp timestamp;

	// konstruktorit
	public Comment() {
		this.message = null;
		this.timestamp = null;
	}
	public Comment(String message, Timestamp timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}
	
	// getterit ja setterit
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	// toString
	@Override
	public String toString() {
		return "Comment [id=" + id + ", message=" + message + ", timestamp=" + timestamp + "]";
	}
}
