function createChart(e) {
    const days = document.querySelectorAll(".chart-values li");
    const tasks = document.querySelectorAll(".chart-bars li");
    const daysArray = [...days];

    tasks.forEach(el => {
        const duration = el.dataset.duration.split("-");
        const startDay = duration[0];
        const endDay = duration[1];
        let left = 0,
            width = 0;

        if (startDay.endsWith("½")) {
            const filteredArray = daysArray.filter(day => day.textContent == startDay.slice(0, -1));
            left = filteredArray[0].offsetLeft + filteredArray[0].offsetWidth / 2;
        } else {
            const filteredArray = daysArray.filter(day => day.textContent == startDay);
            left = filteredArray[0].offsetLeft;
        }

        if (endDay.endsWith("½")) {
            const filteredArray = daysArray.filter(day => day.textContent == endDay.slice(0, -1));
            width = filteredArray[0].offsetLeft + filteredArray[0].offsetWidth / 2 - left;
        } else {
            const filteredArray = daysArray.filter(day => day.textContent == endDay);
            width = filteredArray[0].offsetLeft + filteredArray[0].offsetWidth - left;
        }

        // apply css
        el.style.left = `${left}px`;
        el.style.width = `${width}px`;
        if (e.type == "load") {
            el.style.backgroundColor = el.dataset.color;
            el.style.opacity = 1;
        }
    });
}

//give function array of dates, returns minimum date
function minDate(arr) {
    return new Date(Math.min.apply(null,arr));
}

//give function array of dates, returns max diff int
function maxDifference(arr) {
    //converts dates to time
    var DateTimes = [];
    for (var i = 0; i < arr.length; i++) {
        var date = new Date(arr[i]).getTime();
        DateTimes.push(date);
    }

    //looks for longest time, converts to days after
    let maxDiff = -1;
    let min = arr[0];
    for (let i = 0; i < arr.length; i++) {
        if (arr[i] > min && maxDiff < arr[i] - min) {
            maxDiffInMilliseconds = arr[i] - min;
        }
        if (arr[i] < min) {
            min = arr[i];
        }
    }

    //convert difference of time to days
    var maxDiffInDays = maxDiffInMilliseconds / (1000 * 3600 * 24);

    return maxDiffInDays;
}

//make date array for diagram
function makeGanttDateArray(arr) {
    var startDate = minDate(arr);
    var dateArray = [];
    dateArray.push(startDate)

    for (let i = 0; i < maxDifference(arr); i++) {
        var date = startDate+i;
        dateTimes.push(date);
    }
    return dateTimes;
}

window.addEventListener("load", createChart);
window.addEventListener("resize", createChart);