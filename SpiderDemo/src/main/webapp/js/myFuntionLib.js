var arrays = []; // 存储图片在服务器上的路径

function changeColor() {
	var r = Math.round(Math.random() * 255);
	var g = Math.round(Math.random() * 255);
	var b = Math.round(Math.random() * 255);
	var rgb1 = "rgb" + "(" + r + "," + g + "," + b + ")";
	var rgb2 = "rgb" + "(" + (255 - r) + "," + (255 - g) + "," + (255 - b)
			+ ")";
    $("#header-force").css({
        "background-color" : rgb1,
        "color" : rgb2
    });
    $("#section").css({
        "background-color" : rgb1,
        "color" : rgb2
    });
}

var timer = 0;
function translate() {
    var ele = document.getElementById("header-force");
    if (timer === 12) {
        ele.style.transform = "rotateX(360deg)";
    }
    if (timer === 24) {
        ele.style.transform = "rotateX(0deg)";
        timer = 0
    }
    timer++;
}

var flag = true;
function changeFontSize() {
	if (flag) {
		$("#header-force-txt").css({
			"font-size" : 100 + "px"
		});
		flag = false;
	} else {
		$("#header-force-txt").css({
			"font-size" : 30 + "px"
		});
		flag = true;
	}
}

var h = 0;
var v = 0;
var z = 0;

var x = 0;
var setp = 2;
function transRoate() {
    if (x >= window.innerWidth - 350)
    {
        setp *= -1;
    }
    if (x < 0)
    {
        setp *= -1;
    }
    x += setp;
    var ele = document.getElementById("box-3d");
    ele.style.left = x + "px";
	ele.style.transform = "rotateX(" + h + "deg)" + "rotateY(" + v + "deg)"
			+ "rotateZ(" + z + "deg)";
	h++;
	v++;
	z++;
	if (h == 360) {
		h = 0
	}
	if (v == 360) {
		v = 0;
	}
	if (z == 360) {
		z = 0;
	}
}

function changeBoxImage() {
    var box = document.querySelectorAll("#box-3d div img");
    var index = Math.round(Math.random() * (arrays.length - 1));
    for (i in box) {
        // $("#box-3d div img")[i].attr("src",arrays[index]);
        box[i].src = arrays[index];
    }
}

// 动态显示数据
function showData(parentNode, nickname, watches, imageName, imageURL) {
	var li = document.createElement("li");
	var img = document.createElement("img");
	var span = document.createElement("span");

	var leftspan = document.createElement("span");
	var rightspan = document.createElement("span");

	var leftxt = document.createElement("span");
	var rightxt = document.createElement("span");

	var leftnode = document.createTextNode(nickname);
	var rightnode = document.createTextNode("watches " + watches);

	li.classList.add("active");
	span.classList.add("txt");
	leftspan.classList.add("leftxt");
	rightspan.classList.add("rightxt");

	leftxt.classList.add("dispaly-txt");
	rightxt.classList.add("dispaly-txt");

	img.dataset.original = imageURL;
	img.setAttribute("src", imageName);

	leftxt.appendChild(leftnode);
	rightxt.appendChild(rightnode);

	leftspan.appendChild(leftxt);
	rightspan.appendChild(rightxt);

	span.appendChild(leftspan);
	span.appendChild(rightspan);

	parentNode.appendChild(li);
	li.appendChild(img);
	li.appendChild(span);
}