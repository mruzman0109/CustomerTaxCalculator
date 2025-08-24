# Questions for Congestion Tax Assignment

## 1. Rules & Exceptions
- How exactly should "the day before a public holiday" be defined for 2013? Are there official sources for Sweden/Gothenburg or should we use a local calendar?
- Are all foreign vehicles always exempt, or only from certain countries?
- How should vehicles belonging to multiple exempt categories (e.g., diplomat + motorbike) be treated?
- I see you said that there should be license plate reading, but is it mandatory for the assignment?

## 2. Single Charge Rule
- If a vehicle passes multiple toll stations within **exactly 60 minutes**, is the last second included in the current window or does it start a new window?
- How is the window calculated when passes cross **midnight** (e.g., 23:30–00:20)?

## 3. Tax Rules & Time Intervals
- Are the time intervals **inclusive or exclusive**? (e.g., 06:00–06:29 → does it include 06:29:00?)
- How should overlapping intervals or future-added intervals be handled?

## 4. Multiple Cities / Configurable Rules
- If new cities introduce congestion tax, what is the recommended **external datastore**: JSON, DB, YAML, or something else? 
I've added a JSON example in the project, alongside with a database integration, but still there is no clear instruction.
- Should the rules support **dynamic runtime loading** or only be read at application startup?

## 5. Input & Validation
- How should the input JSON be validated, especially for vehicle type and date formats? I created basic validation on Jackson level, but is it enough?
- What should happen if a vehicle has a **non-existent type** or an **empty list of dates**?

## 6. Testing / Edge Cases
- What are the expected minimal **unit tests**? I created some, but should I cover more edge cases?
- Should **integration tests** be included, especially for database interactions?
- How should a **large number of passes in a single day** be handled? Is performance optimization required?

## 7. Miscellaneous
- Should time zones be supported or should we always use Gothenburg local time?
- How should the **maximum daily fee (60 SEK)** be applied if multiple vehicles have the same registration plate on the same day?  
