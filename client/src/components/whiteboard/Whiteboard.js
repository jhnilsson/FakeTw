import {useEffect, useRef} from 'react';

const Whiteboard = () => {
  const canvasRef = useRef(null);
  const colorsRef = useRef(null);
  const socketRef = useRef();

  useEffect(() => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");

    const colors = document.getElementsByClassName("color");

    const current = {
      color: "black",
    };

    const onColorChange = (event) => {
      current.color = event.target.className.split(" ")[1];
    }

    let drawing = false;

    const drawLine = (x1, y1, x2, y2, color, emit) => {
      context.beginPath();
      context.moveTo(x1, y1);
      context.lineTo(x2, y2);
      context.strokeStyle = "#000000";
      context.lineWidth = 2;
      context.stroke();
      context.closePath();

      if(!emit){
        return;
      }
      const w = canvas.width;
      const h = canvas.height;

    }

    const onMouseDown = (event) => {
      event.preventDefault();
      drawing = true;
      current.x = event.clientX ||event.touches[0].clientX;
      current.y = event.clientY ||event.touches[0].clientY;
    };

    const onMouseMove = (event) => {
      event.preventDefault();
      if(!drawing){
        return;
      }
      drawLine(current.x, current.y, event.clientX ||event.touches[0].clientX, event.clientY ||event.touches[0].clientY, current.color, false);
      current.x = event.clientX ||event.touches[0].clientX;
      current.y = event.clientY ||event.touches[0].clientY;
    }

    const onMouseUp = (event) => {
      event.preventDefault();
      if(!drawing){
        return;
      }
      drawing = false;
      drawLine(current.x, current.y, event.clientX || event.touches[0].clientX, event.clientY || event.touches[0].clientY, current.color, false);
    }

    canvas.addEventListener('mousedown', onMouseDown, false);
    canvas.addEventListener('mouseup', onMouseUp, false);
    canvas.addEventListener('mouseout', onMouseUp, false);
    canvas.addEventListener('mousemove', onMouseMove, false);

    const onResize = () => {
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
    };

    window.addEventListener('resize', onResize, false);
    onResize();

  })

  return (
      <div>
        <canvas ref={canvasRef} className="whiteboard" />
      </div>
  )
}

export default Whiteboard;