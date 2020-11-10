/*
 * Copyright (c) 2020. This project is created by Antonio Torres using MIT Open Source licence. You can use it just for educational purposes.
 *
 */

package Model;

import java.util.Vector;

public class Particle {
    public int id;

    private Vector<Double> position;
    private Vector<Double> velocity;
    private Vector<Double> bestPosition;
    private Double fitness, bestFitness;

    public Particle() {
    }

    public Particle(int id, Vector<Double> position, Vector<Double> velocity, Vector<Double> bestPosition, Double fitness, Double bestFitness) {
        this.id = id;
        this.position = new Vector<>(position);
        this.velocity = new Vector<>(velocity);
        this.bestPosition = new Vector<>(bestPosition);
        this.fitness = fitness;
        this.bestFitness = bestFitness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<Double> getPosition() {
        return position;
    }

    public void setPosition(Vector<Double> position) {
        this.position = position;
    }

    public Vector<Double> getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector<Double> velocity) {
        this.velocity = velocity;
    }

    public Vector<Double> getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(Vector<Double> bestPosition) {
        this.bestPosition = bestPosition;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(Double bestFitness) {
        this.bestFitness = bestFitness;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", position=" + position +
                ", velocity=" + velocity +
                ", bestPosition=" + bestPosition +
                ", fitness=" + fitness +
                ", bestFitness=" + bestFitness +
                '}';
    }

}
