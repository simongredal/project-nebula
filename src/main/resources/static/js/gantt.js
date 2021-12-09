//example:
//https://webdesign.tutsplus.com/tutorials/build-a-simple-gantt-chart-with-css-and-javascript--cms-33813

function createChart(e) {
    const days = document.querySelectorAll(".chart-values li");
    const tasks = document.querySelectorAll(".chart-bars li");
    const daysArray = [...days];

    tasks.forEach(el => {
        const duration = el.dataset.duration.split("/");
        const startDate = duration[0].split(" ");
        const endDate = duration[1].split(" ");
        const startDay = startDate[0];
        const endDay = endDate[0];
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
        if (e.type == "click") {
            el.style.backgroundColor = el.dataset.color;
            el.style.opacity = 1;
        }
    });
}

window.addEventListener("load", createChart);
window.addEventListener("resize", createChart);
window.addEventListener("click", createChart);