package io.illyria.factionsx.manager;

import io.illyria.factionsx.entity.Faction;
import io.illyria.factionsx.entity.IFaction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class FactionManager extends AbstractManager<IFaction> {

    private Set<IFaction> factions;

    public FactionManager() {
        factions = new HashSet<>();
    }

    @Override
    public Set<IFaction> getAll() {
        if (factions == null) return new HashSet<>();
        return factions;
    }

    @Override
    public IFaction getByName(String name) {
        return this.getAll().stream().parallel().filter(faction -> faction.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public IFaction getById(String id) {
        return this.getAll().parallelStream().filter(faction -> faction.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void load() {
       factions = persistenceEngine.getFactionPersitence().getAll();
    }

    @Override
    public void save() {
        persistenceEngine.getFactionPersitence().saveAll();
    }

    public IFaction createFaction(String factionName, String ownerName) {
        IFaction faction = new Faction(generateFactionId(), factionName, ownerName);
        if (factions == null) factions = new HashSet<>();
        factions.add(faction);
        return faction;
    }

    private String generateFactionId() {
        return UUID.randomUUID().toString();
    }

}
