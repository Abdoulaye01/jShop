package shop.main.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "categoryName", nullable = false, length = 50)
	private String categoryName;

	@NotBlank
	@Column(name = "categoryURL", nullable = false, length = 100, unique = true)
	private String categoryURL;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "meta_title", nullable = true)
	private String metaTitle;

	@Column(name = "meta_description", nullable = true)
	private String metaDescription;

	@Column(name = "status", nullable = false)
	private boolean status;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Product> products;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(columnDefinition = "integer", name = "parent_id", nullable = true)
	private Category parentCategory;

	@OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
	private List<Category> children;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((categoryURL == null) ? 0 : categoryURL.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((metaDescription == null) ? 0 : metaDescription.hashCode());
		result = prime * result + ((metaTitle == null) ? 0 : metaTitle.hashCode());
		result = prime * result + ((parentCategory == null) ? 0 : parentCategory.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (categoryURL == null) {
			if (other.categoryURL != null)
				return false;
		} else if (!categoryURL.equals(other.categoryURL))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (metaDescription == null) {
			if (other.metaDescription != null)
				return false;
		} else if (!metaDescription.equals(other.metaDescription))
			return false;
		if (metaTitle == null) {
			if (other.metaTitle != null)
				return false;
		} else if (!metaTitle.equals(other.metaTitle))
			return false;
		if (parentCategory == null) {
			if (other.parentCategory != null)
				return false;
		} else if (!parentCategory.equals(other.parentCategory))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", categoryURL=" + categoryURL
				+ ", description=" + description + ", metaTitle=" + metaTitle + ", metaDescription=" + metaDescription
				+ ", status=" + status + ", parentCategory=" + (parentCategory != null ? parentCategory.getId() : null)
				+ ", children=" + (children != null ? children.size() : null) + "]";
	}

	public boolean isNew() {
		return (this.id == null);
	}

}
