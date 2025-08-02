package com.example.demo.repository;

import com.example.demo.model.entity.EntryWayPanel;
import com.example.demo.model.entity.ExitWayPanel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryWayPanelRepository extends JpaRepository<EntryWayPanel,Long> {
}
