import serial
import time
import requests
import json
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

firebase_url = 'https://imonitor-eff27-default-rtdb.firebaseio.com/'
fixed_interval = 1

arduino = serial.Serial(port='/dev/cu.usbmodem142101', baudrate=115200)  # Establish the connection on a specific port

logger.info('Scraping Arduino data')

list = []
while True:

    # current time and date
    time_hhmmss = time.strftime('%H:%M:%S')
    date_mmddyyyy = time.strftime('%d/%m/%Y')

    arduino.flushInput()
    currentLine = arduino.readline()
    currentLine = currentLine.decode().strip().split(";")
    list = currentLine
    try:
        if list[0] == "0":
            print("Arduino Connection failed")
            break

        if len(list) > 1:
            id = list[0]
            heartRate = list[1]
            spo2 = list[2]
            temp = list[3]
            data = {'id': id, 'date': date_mmddyyyy, 'time': time_hhmmss, 'heart_rate': heartRate, 'blood_oxygen': spo2,
                    'temperature': temp}
            results = requests.post(firebase_url + '/' + id + '/data.json', data=json.dumps(data))
            time.sleep(fixed_interval)
            print(id, heartRate, spo2, temp)

    except Exception as e:
        logger.info(e)

    time.sleep(fixed_interval)
