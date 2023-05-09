package ua.foxminded.springdatajpa.school.dao.interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.springdatajpa.school.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

	@Query("SELECT g FROM Group g JOIN Student s ON g.id = s.groupId GROUP BY g HAVING COUNT(s) <= :students")
	List<Group> findGroupsWithLessOrEqualsStudents(@Param("students") long maxStudents);

	@Query("SELECT g FROM Group g WHERE g.groupName = :groupName")
	Optional<Group> findByGroupName(@Param("groupName") String groupName);

	@Modifying
	@Query("UPDATE Group c SET c.groupName = :newGroupName WHERE c.groupName = :groupName")
	int editGroupName(@Param("groupName") String groupName, @Param("newGroupName") String newGroupName);

	@Modifying
	@Query("DELETE FROM Group c WHERE c.groupName = :groupName")
	int deleteGroupByName(@Param("groupName") String groupName);

}
