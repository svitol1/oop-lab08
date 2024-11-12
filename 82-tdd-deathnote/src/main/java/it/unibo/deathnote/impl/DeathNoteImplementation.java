package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{
    private Map<String, DeathSpecs> deathNote = new HashMap<>();
    private static final String DEF_DEATH = "heart attack";
    private static final int TIME_FOR_CAUSE = 40;
    private static final int TIME_FOR_DETAILS = 6000 + TIME_FOR_CAUSE;
    private String lastNameWritten = null;

    private class DeathSpecs{
        private String causeOfDeath;
        private String deathDetails;
        private boolean isCauseModified = false; 
        private final long start = System.currentTimeMillis();
    }

    @Override
    public String getRule(int ruleNumber) {
        try {
            return RULES.get(ruleNumber - 1);    
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Invalid input");
        }
    }

    @Override
    public void writeName(String name) {
        try {
            if(!this.isNameWritten(Objects.requireNonNull(name))) {
                this.deathNote.put(name, new DeathSpecs());
                this.lastNameWritten = name;
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("Invalid input name");
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        try {
           if( !isCauseModified(lastNameWritten) && System.currentTimeMillis() - getStart(lastNameWritten) < TIME_FOR_CAUSE) {
                this.deathNote.get(lastNameWritten).causeOfDeath = Objects.requireNonNull(cause);
                this.deathNote.get(lastNameWritten).isCauseModified = true;
                return true;
            }
        } catch (NullPointerException e) {
            throw new IllegalStateException("Invalid cause passed as input");
        }
        
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        try {
            if(System.currentTimeMillis() - getStart(lastNameWritten) < TIME_FOR_DETAILS){
                this.deathNote.get(lastNameWritten).deathDetails = Objects.requireNonNull(details);
                return true;
            }
        } catch (NullPointerException e) {
            throw new IllegalStateException("Invalid input passed as input");
        }

        return false;
    }

    @Override
    public String getDeathCause(String name) {
        if(this.isNameWritten(name)){
            updateDeaths(name);
            return this.deathNote.get(name).causeOfDeath;
        }
        throw new IllegalArgumentException("That name isn't in the book");
    }

    @Override
    public String getDeathDetails(String name) {
        if(this.isNameWritten(name)){
            return this.deathNote.get(name).deathDetails;
        }
        throw new IllegalArgumentException("That name isn't in the book");
    }

    @Override
    public boolean isNameWritten(String name) {
        try {
            return this.deathNote.containsKey(Objects.requireNonNull(name));    
        } catch (NullPointerException e) {
            throw new NullPointerException("Input is null");
        }
    }

    @Override
    public int getNamesWritten() {
        return this.deathNote.size();
    }

    private void updateDeaths(String name) {
        if(name != null && !isCauseModified(name)) {
            this.deathNote.get(name).causeOfDeath = DEF_DEATH;
            this.deathNote.get(name).isCauseModified = true;
        }
    }

    private long getStart(String name){
        return this.deathNote.get(Objects.requireNonNull(name)).start;
    }

    private boolean isCauseModified(String name) {
        return this.deathNote.get(Objects.requireNonNull(name)).isCauseModified;
    }
}