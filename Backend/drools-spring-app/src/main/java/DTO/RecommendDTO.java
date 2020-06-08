package DTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class RecommendDTO {
	
	List<RecommendedItem> recommendations = new ArrayList<>();

	public RecommendDTO(List<RecommendedItem> recommendations) {
		super();
		this.recommendations = recommendations;
	}

	public RecommendDTO() {
	}

	public List<RecommendedItem> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<RecommendedItem> recommendations) {
		this.recommendations = recommendations;
	}

}
