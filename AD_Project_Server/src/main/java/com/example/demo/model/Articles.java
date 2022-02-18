package com.example.demo.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.ocpsoft.prettytime.PrettyTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Articles {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE}, fetch=FetchType.EAGER)
	private Source source;	
	private String author;
	@Column(length=512)
	private String title;
	@Column(length=512)
	private String description;
	@Column(length=512)
	private String url;
	@Column(length=1000)
	private String urlToImage;
	private String publishedAt;
	private String prettytime;
	private String content;
	@OneToMany(fetch=FetchType.EAGER)
	private List<Comment> comments;	
	@ManyToOne(fetch=FetchType.EAGER)
	private Category category;
	
	public String getPrettytime() {
		Instant dateTime = Instant.parse(publishedAt);
		PrettyTime p = new PrettyTime();
		return p.format(dateTime);
	}
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                title.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Articles another = (Articles) obj;
        if(obj == null) return false;
        if(title.equals(another.title)){
            return true;
        }
        return false;
    }

}

