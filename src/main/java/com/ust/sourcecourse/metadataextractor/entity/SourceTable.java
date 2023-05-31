
package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "source_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourceTable {

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@ManyToOne
	@JoinColumn(name = "datasource_uid", referencedColumnName = "uid")
	private DataSource dataSource;

	@Column(name = "rowCount")
	private Long rowCount;

	@Column(name = "size")
	private String size;

	@Column(name = "minDate")
	private LocalDate minDate;

	@Column(name = "maxDate")
	private LocalDate maxDate;

	@Column(name = "yoyCount")
	private Long yoyCount;

	@Column(name = "momCount")
	private Long momCount;

	@ElementCollection
	private List<String> tags;

	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;

	@Column(name = "created_timestamp")
	@CreationTimestamp
	private LocalDateTime createdTimestamp;

	@Column(name = "modified_by")
	@LastModifiedBy
	private String modifiedBy;

	@Column(name = "modified_timestamp")
	@UpdateTimestamp
	private LocalDateTime modifiedTimestamp;

	@OneToMany(mappedBy = "sourceTable", cascade = CascadeType.ALL)
	private List<SourceColumn> sourceColumns;

	@OneToMany(mappedBy = "sourceTable")
	private List<ProjectTable> projectTables;

	@Column(name = "sample_data", columnDefinition = "json")
	@Type(JsonType.class)
	private String sampleData;

}
