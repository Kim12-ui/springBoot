package com.dsa.web6.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ajax_member")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
	@Id
	@Column(name = "id", length = 30)
	String id;
	
	@Column(name = "name", length = 30, nullable = false)
	String name;
}
