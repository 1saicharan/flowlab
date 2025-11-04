# flowlab
# FlowLab — Kotlin Flow & Channel Experiments

A small playground for learning **Kotlin Flow** behaviors, side effects, and **Channel** backpressure.
Each sprint explores a specific concept with runnable UI and tests.

---

## 🧩 Project Overview

| Sprint | Focus | What You Learn |
|---------|--------|----------------|
| **1** | Hot vs Cold Flows | `stateIn`, `shareIn`, `MutableSharedFlow`, `WhileSubscribed`, collector behavior |
| **2** | Combine & Backpressure | `combine`, `flatMapLatest`, `buffer`, `conflate`, `collectLatest`, cancellation and CPU-heavy work |
| **3** | Lifecycle & Channels | `repeatOnLifecycle`, `catch`, `onCompletion`, bridging `Channel` → `Flow`, structured concurrency |

Each sprint has a ViewModel, a Compose screen, and a corresponding test file.

---

## ⚙️ Tech Stack
- **Kotlin Coroutines & Flow**
- **Jetpack Compose**
- **ViewModel & Lifecycle**
- **JUnit + kotlinx-coroutines-test**
- **app.cash.turbine** (Flow testing)

---

## 🏗️ Project Structure
app/
├─ ui/
│ ├─ Screen1.kt
│ ├─ Screen2.kt
│ └─ Screen3.kt
├─ viewmodel/
│ ├─ MainViewModel1.kt
│ ├─ MainViewModel1.kt
│ └─ MainViewModel1.kt
├─ test/
│ ├─ Screen1Test.kt
└─ MainActivity.kt

## 📚 Notes
- **Cold Flow** = restarts producer for every collector.
- **Hot Flow** = shared producer (`shareIn` / `stateIn`).
- **Channel capacities**
    - `RENDEZVOUS` → 0-buffer, producer waits for consumer
    - `BUFFERED` → small buffer (default 64)
    - `UNLIMITED` → infinite queue, no backpressure
    - `CONFLATED` → keeps only latest value

Modify producer/consumer delays in `Sprint2` or `Sprint3` to visualize backpressure differences.

---

## 🧠 What You’ll Learn
By completing all sprints you should:
- Understand Flow cold/hot semantics and sharing.
- Recognize how buffering and conflation affect emission timing.
- Build lifecycle-safe collectors with `repeatOnLifecycle`.
- Handle exceptions and cleanup with `catch` and `onCompletion`.
- Use `Channel` for imperative communication and bridge it to `Flow`.