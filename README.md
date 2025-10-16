# 🗓️ Meeting Scheduler System

![Java](https://img.shields.io/badge/Language-Java-orange)
![Concurrency](https://img.shields.io/badge/Feature-Thread%20Safe-green)
![DataStructure](https://img.shields.io/badge/Data%20Structure-TreeSet-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Status](https://img.shields.io/badge/Build-Stable-success)

---

## 📖 Table of Contents
1. [Overview](#-overview)
2. [Features](#-features)
3. [Architecture & Components](#-architecture--components)
4. [Example Scenario](#-example-scenario)
5. [Time & Space Complexity](#-time--space-complexity)
6. [Concurrency Design](#-concurrency-design)
7. [Technologies Used](#-technologies-used)
8. [How to Run](#-how-to-run)
9. [Future Improvements](#-future-improvements)
10. [Author](#-author)

---

## 📘 Overview
The **Meeting Scheduler** is an optimized system that allocates meetings to rooms by minimizing **spillage** (unused free time gaps) while maintaining **thread safety** for concurrent scheduling.

It’s built as a scalable, production-grade version of the classic *Meeting Room Scheduling* interview problem — designed for real-world extensibility such as capacity constraints, fairness policies, and concurrent booking.

---

## 🚀 Features

✅ **Dynamic Scheduling** — Allocate meetings to available rooms automatically.  
🧩 **Spillage Minimization** — Select the most time-efficient room (minimal leftover time).  
🧵 **Thread Safety** — Concurrent meeting booking using `ReentrantReadWriteLock`.  
⚙️ **Singleton Pattern** — Centralized scheduler instance.  
📊 **Optimized Data Structures** — Uses `TreeSet` for efficient interval search and insertion.  
📈 **Scalable Design** — Easily extendable to multi-threaded enterprise setups.

---

## 🏗️ Architecture & Components

| Component | Responsibility |
|------------|----------------|
| **`Room`** | Represents a meeting room. Maintains its own schedule (`TreeSet<Interval>`) and supports thread-safe operations. |
| **`Interval`** | Models a start and end time for a meeting or available slot. Provides validation and readability. |
| **`MeetingScheduler`** | The core service responsible for picking the optimal room using spillage minimization and scheduling the meeting. |
| **`Main`** | CLI entry point for simulation — handles user input and triggers scheduling. |

---

## 🧠 Example Scenario

### Input:

Number of rooms: 2
Meetings:

09:00 - 10:00

09:00 - 09:30

10:00 - 11:00

11:00 - 12:00


### Output:
Room 0: [09:00 - 10:00, 10:00 - 11:00, 11:00 - 12:00]

Room 1: [09:00 - 09:30]


🟢 Here, the scheduler assigns shorter meetings to partially filled rooms, avoiding large time waste (spillage) in long-available rooms.

---

## 🧮 Time & Space Complexity

Let:
- **R** = number of rooms  
- **M** = number of meetings per room  
- **N** = total number of meetings  

| Operation | Description | Time Complexity | Space Complexity |
|------------|--------------|-----------------|------------------|
| Insert Meeting | Find available room and insert interval | `O(R * log M)` | `O(M)` per room |
| Check Availability | Binary search in `TreeSet` | `O(log M)` | `O(1)` |
| Spillage Optimization | Compare across all rooms | `O(R * log M)` | `O(1)` |
| Concurrent Scheduling | Each thread locks its target room | `O(1)` contention if non-overlapping | `O(R)` for locks |

> **Overall:**  
> ⏱️ Average Case: `O(N * R * log M)`  
> 💾 Space: `O(R * M)`

---

## 🧵 Concurrency Design

Each `Room` uses:
```java
private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

Read Lock: Multiple threads can check availability simultaneously.

Write Lock: Only one thread modifies a room’s schedule at a time.

Enables fine-grained locking, reducing contention and improving scalability for parallel meeting requests.


| Category             | Tools                  |
| -------------------- | ---------------------- |
| **Language**         | Java 17+               |
| **Core Collections** | TreeSet, NavigableSet  |
| **Concurrency**      | ReentrantReadWriteLock |
| **Design Pattern**   | Singleton              |
| **Future Logging**   | SLF4J / Logback        |
| **Testing**          | JUnit 5 (optional)     |

# Clone the repository
git clone https://github.com/<your-username>/MeetingScheduler.git
cd MeetingScheduler

# Compile the code
javac src/**/*.java

# Run interactively
java Main

