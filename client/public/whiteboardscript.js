var canvas;
var context;
var messageManager;

var isDrawing = false;
var defaultColor = "#000000";

var localPenLocation = {};
var localColor = defaultColor;

window.onload = init;

function init(){
  initWhiteboard();
  addListeners();
}

function initWhiteboard() {
  canvas = document.getElementById("whiteboard");
  canvas.width = 800;
  canvas.height = 600;

  context = canvas.getContext("2d");
}

function addListeners() {
  canvas.onmousedown = mouseDownListener;
  canvas.onmousemove = mouseMoveListener;
  canvas.onmouseup = mouseUpListener;
}

