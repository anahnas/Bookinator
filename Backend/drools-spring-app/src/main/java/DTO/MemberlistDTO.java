package DTO;

import java.util.ArrayList;
import java.util.List;

import sbnz.integracija.example.facts.Member;

public class MemberlistDTO {
	private List<Member> members = new ArrayList<>();

	public MemberlistDTO() {
		super();
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	

}
