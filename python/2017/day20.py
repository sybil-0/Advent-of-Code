import re
from dataclasses import dataclass


@dataclass
class Particle:
    id: int
    x: int
    y: int
    z: int
    x_vel: int
    y_vel: int
    z_vel: int
    x_acc: int
    y_acc: int
    z_acc: int

    def update(self):
        self.x_vel += self.x_acc
        self.y_vel += self.y_acc
        self.z_vel += self.z_acc
        self.x += self.x_vel
        self.y += self.y_vel
        self.z += self.z_vel

    def prox_center(self):
        return abs(self.x) + abs(self.y) + abs(self.z)


with open("day20.txt", "r") as infile:
    data = infile.read().splitlines()
    particles = []
    for i, line in enumerate(data):
        xs = [int(x) for x in re.findall(r"-?\d+", line)]
        particles.append(Particle(i, *xs))

    for _ in range(1000):
        for p in particles:
            p.update()

    print(min(particles, key=lambda p: p.prox_center()))
