# ORIG2025 - Open Robotics Intelligent Grid 2025

## Team MODUS VIVENDI

This repository contains the robot code for **Team MODUS VIVENDI** participating in the **Open Robotics Intelligent Grid 2025** competition. This marks our **third consecutive year** participating in this prestigious robotics event.

## About Open Robotics Intelligent Grid 2025

The 7th edition of "Open Robotics Intelligent Grid" takes place September 1-4, 2025, at the West University of Timișoara, organized by the Faculty of Computer Science, Department of Research, Development and Innovation, in collaboration with the robotics team CSH from the "Carmen Sylva" National Pedagogical College. The event brings together 29 robotics teams from Romania and Moldova to demonstrate their creativity and technical skills.

### Event Highlights

- **Challenge**: Build a robot from scratch based on a game theme revealed on the first day of the event
- **Theme 2025**: "Whispered secrets hide beneath the shifting sands"
- **Duration**: 72 hours from the opening ceremony for approximately 250 robotics enthusiasts
- **Opening Ceremony**: Monday, September 1, starting at 10:30 AM in UVT's Aula Magna
- **Final Competition**: Thursday, September 4, at Iulius Mall with public demonstration matches featuring four robots on the field

### Competition Format

The competition features demonstration matches where robots built in just three days compete on the field, performing various tasks to accumulate points. Beyond technical performance awards, teams can win special prizes awarded by FIRST Tech Challenge judges who evaluate creativity, strategy, and robot design.

---

## Technical Information

This repository is based on the FTC SDK for robot development and contains the source code used to build an Android app to control a FIRST Tech Challenge competition robot.

### Requirements
- Android Studio 2021.2 (codename Chipmunk) or later
- Android 6.0 (API level 23) or higher

### Getting Started
If you are new to robotics or new to the FIRST Tech Challenge, consider reviewing the [FTC Blocks Tutorial](https://ftc-docs.firstinspires.org/programming_resources/blocks/Blocks-Tutorial.html) to get familiar with the control system.

### Key Resources
- [FIRST Tech Challenge Documentation](https://ftc-docs.firstinspires.org/index.html)
- [FTC Javadoc Documentation](https://javadoc.io/doc/org.firstinspires.ftc)
- [FIRST Tech Challenge Community](https://ftc-community.firstinspires.org/)

### Sample OpModes
This project contains sample OpModes (robot code examples) in the `/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples` folder that can be copied to your `/TeamCode` folder and modified for your team's needs.

## Project Structure

```
ORIG2025/
├── FtcRobotController/    # Core FTC SDK files
├── TeamCode/              # Your team's custom OpModes
├── doc/                   # Documentation
├── gradle/                # Gradle wrapper files
├── libs/                  # Additional libraries
└── README.md              # This file
```

## Competition History

**Team MODUS VIVENDI** has been competing in the Open Robotics Intelligent Grid since 2023:
- **2023**: First participation
- **2024**: Second consecutive year
- **2025**: Third consecutive year (current)

---

## NOTICE

This repository contains the public FTC SDK adapted for the Open Robotics Intelligent Grid 2025 competition. The base FTC SDK was originally designed for the CENTERSTAGE (2023-2024) competition season and has been customized for our specific competition needs.

## Version Information

This project is based on FTC SDK Version 9.0.1 (20230929-083754) with team-specific modifications for the ORIG2025 competition.

### Key Features of This Version
- Support for AprilTag detection and navigation
- Enhanced VisionPortal API for computer vision
- Improved motor control with PIDF coefficients
- Support for REV Robotics Control Hub and Expansion Hub
- Advanced gamepad features including rumble and LED control
- TensorFlow Lite integration for object detection

---

**Good luck to all teams participating in Open Robotics Intelligent Grid 2025!**

*"Whispered secrets hide beneath the shifting sands" - ORIG 2025*