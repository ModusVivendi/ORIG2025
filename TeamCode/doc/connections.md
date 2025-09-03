# Gobilda Motor Hub Connection Manual

## Overview
This document provides detailed information about the motor connections between Gobilda driver motors and the REV Robotics Control Hub and Expansion Hub system.

---

## System Architecture

### Hub Configuration
- **Control Hub**: Primary controller with built-in Android device
- **Expansion Hub**: Secondary hub for additional motor/servo ports
- **Connection**: Hubs connected via RS485 communication cable

---

## Motor Port Assignments

### Control Hub (Primary Hub)
| Port | Motor Name | Motor Type | Function | Status |
|------|------------|------------|----------|---------|
| 0 | **Front Right (FR)** | Gobilda Drive Motor | Right front wheel drive | ✅ Connected |
| 1 | *Not Connected* | - | Available for expansion | ⚪ Open |
| 2 | **Back Right (BR)** | Gobilda Drive Motor | Right rear wheel drive | ✅ Connected |
| 3 | **Slider Right (VSR)** | Gobilda Drive Motor | Right vertical slider mechanism | ✅ Connected |

### Expansion Hub (Secondary Hub)
| Port | Motor Name | Motor Type | Function | Status |
|------|------------|------------|----------|---------|
| 0 | **Front Left (FL)** | Gobilda Drive Motor | Left front wheel drive | ✅ Connected |
| 1 | *Not Connected* | - | Available for expansion | ⚪ Open |
| 2 | **Back Left (BL)** | Gobilda Drive Motor | Left rear wheel drive | ✅ Connected |
| 3 | **Slider Left (VSL)** | Gobilda Drive Motor | Left vertical slider mechanism | ✅ Connected |

---

## Drive System Configuration

### Drivetrain Layout
```
    FL ---- FR
    |        |
    |  ROBOT |
    |        |
    BL ---- BR
```

### Motor Groupings
- **Left Drive Motors**: FL (Expansion Port 0) + BL (Expansion Port 2)
- **Right Drive Motors**: FR (Control Port 0) + BR (Control Port 2)
- **Slider System**: VSL (Expansion Port 3) + VSR (Control Port 3)

---

## Technical Specifications

### Gobilda Motor Details
- **Motor Type**: Gobilda 5202/5203 Series (or specify your exact model)
- **Encoder**: Built-in encoder for position feedback
- **Voltage**: 12V DC
- **Current Rating**: [Specify amperage]
- **Gear Ratio**: [Specify if applicable]

### Connection Requirements
- **Cable Type**: REV Robotics motor cables
- **Connector**: JST VH 6-pin connector
- **Wire Gauge**: 18 AWG for motor power, 26 AWG for encoder signals

---

## Wiring Diagram

### Control Hub Connections
```
Control Hub
┌─────────────────────┐
│ Port 0: FR Motor    │ ← Front Right Drive
│ Port 1: [OPEN]      │
│ Port 2: BR Motor    │ ← Back Right Drive  
│ Port 3: VSR Motor   │ ← Right Slider
└─────────────────────┘
```

### Expansion Hub Connections
```
Expansion Hub
┌─────────────────────┐
│ Port 0: FL Motor    │ ← Front Left Drive
│ Port 1: [OPEN]      │
│ Port 2: BL Motor    │ ← Back Left Drive
│ Port 3: VSL Motor   │ ← Left Slider
└─────────────────────┘
```

---

## Motor Direction & Encoder Configuration

### Drive Motors
| Motor | Hub | Port | Direction | Encoder Direction |
|-------|-----|------|-----------|-------------------|
| FL | Expansion | 0 | Forward | Normal |
| FR | Control | 0 | Reverse | Normal |
| BL | Expansion | 2 | Forward | Normal |
| BR | Control | 2 | Reverse | Normal |

### Slider Motors
| Motor | Hub | Port | Direction | Encoder Direction |
|-------|-----|------|-----------|-------------------|
| VSL | Expansion | 3 | Forward | Normal |
| VSR | Control | 3 | Forward | Normal |

---

## Programming Configuration

### FTC SDK Motor Mapping
```java
// Control Hub Motors
frontRight = hardwareMap.get(DcMotor.class, "frontRight");
backRight = hardwareMap.get(DcMotor.class, "backRight");
sliderRight = hardwareMap.get(DcMotor.class, "sliderRight");

// Expansion Hub Motors  
frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
backLeft = hardwareMap.get(DcMotor.class, "backLeft");
sliderLeft = hardwareMap.get(DcMotor.class, "sliderLeft");
```

### Hardware Configuration Names
- **frontRight** → Control Hub Port 0
- **backRight** → Control Hub Port 2
- **sliderRight** → Control Hub Port 3
- **frontLeft** → Expansion Hub Port 0
- **backLeft** → Expansion Hub Port 2
- **sliderLeft** → Expansion Hub Port 3

---

## Available Ports for Future Expansion

### Control Hub
- **Port 1**: Available for additional motor/servo

### Expansion Hub  
- **Port 1**: Available for additional motor/servo

### Expansion Possibilities
- Additional intake motors
- Arm/lift mechanisms
- Accessory drives
- Servo-powered mechanisms

---

## Troubleshooting

### Common Issues
1. **Motor Not Responding**
   - Check physical cable connection
   - Verify hardware configuration matches port assignment
   - Test with different cable

2. **Incorrect Motor Direction**
   - Use `setDirection(DcMotorSimple.Direction.REVERSE)` in code
   - Or physically swap motor wires (red/black)

3. **Encoder Issues**
   - Ensure encoder cables are securely connected
   - Check for damaged encoder wires
   - Verify encoder direction in code

### Connection Verification Checklist
- [ ] All motor cables securely connected
- [ ] Hub power connections stable
- [ ] RS485 cable between hubs connected
- [ ] Hardware configuration matches physical setup
- [ ] Motor directions tested and configured

---

## Maintenance Notes

### Regular Checks
- Inspect cable connections monthly
- Check for loose connectors
- Monitor motor performance
- Update hardware configuration as needed

### Documentation Updates
- **Last Updated**: [Current Date]
- **System Version**: [FTC SDK Version]
- **Hardware Revision**: [Note any changes]

---

## Emergency Procedures

### Motor Disconnection
1. Power down robot immediately
2. Check for loose connections
3. Verify no damaged cables
4. Reconnect securely before powering up

### Hub Communication Loss
1. Check RS485 cable between hubs
2. Power cycle both hubs
3. Verify hub addressing in software

---

*This manual should be updated whenever motor configurations change or new components are added to the system.*

