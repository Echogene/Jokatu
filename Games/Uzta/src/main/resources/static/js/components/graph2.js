const JGraph2Proto = Object.create(HTMLElement.prototype);

JGraph2Proto.attachedCallback = function() {

	const template = document.querySelector('#graph2-template');
	const clone = document.importNode(template.content, true);

	this._nodeContainer = clone.querySelector('.nodes');
	this._edgeContainer = clone.querySelector('.edges');

	this.appendChild(clone);

	this._nodeElementName = this.getAttribute('nodeElement');
	this._edgeElementName = this.getAttribute('edgeElement') || 'JLine';
	const destination = this.getAttribute('destination');

	/**
	 * Attributes to set on the node elements when they are created.
	 * @private
	 * @type {Object}
	 */
	this._defaultNodeAttributes = JSON.parse(this.getAttribute('data-defaultNodeAttributes'));
	/**
	 * Attributes to set on the edge elements when they are created.
	 * @private
	 * @type {Object}
	 */
	this._defaultEdgeAttributes = JSON.parse(this.getAttribute('data-defaultEdgeAttributes'));

	const initialData = JSON.parse(this.getAttribute('data-initial'));
	this._redrawGraph(initialData && initialData.payload);

	socket.subscribe(destination, this._redrawGraph.bind(this));
};

/**
 * @param {{nodes: Array.<{id: string, x: number, y: number}>, edges: Array.<Array>}} graph
 * @private
 */
JGraph2Proto._redrawGraph = function(graph) {
	if (!graph) {
		return;
	}
	const nodes = graph.nodes;
	this._updateNodes(nodes);

	const edges = graph.edges;
	this._updateEdges(edges);
};

JGraph2Proto._updateNodes = function(nodes) {
	this._ensureEnoughNodes(nodes.length);
	for (let i = this._nodeContainer.childNodes.length - 1; i >= 0; i--) {
		const element = this._nodeContainer.childNodes[i];
		const node = nodes[i];
		if (node) {
			this._updateNode(element, node);
		} else {
			// Remove the element.
			this._nodeContainer.removeChild(element);
		}
	}
};

JGraph2Proto._ensureEnoughNodes = function(number) {
	const elementsNeeded = number - this._nodeContainer.childNodes.length;
	if (elementsNeeded > 0) {
		for (let i = 0; i < elementsNeeded; i++) {
			this._createNode();
		}
	}
};

JGraph2Proto._createNode = function() {
	const newElement = document.createElement('circle');
	newElement.setAttribute('r', '5');
	newElement.setAttribute('fill', 'black');
	newElement.setAttribute('stroke', 'red');
	this._nodeContainer.appendChild(newElement);
};

JGraph2Proto._updateNode = function(element, node) {
	element.id = node.id;
	element.setAttribute('cx', node.x);
	element.setAttribute('cy', node.y);
	element.className = node.type.toLowerCase();
	if (node.highlighted) {
		element.classList.add('highlighted')
	}

	element.setAttribute('data-node', JSON.stringify(node));
};

JGraph2Proto._updateEdges = function(edges) {
	this._ensureEnoughEdges(edges.length);
	for (let i = this._edgeContainer.childNodes.length - 1; i >= 0; i--) {
		const line = this._edgeContainer.childNodes[i];
		const edge = edges[i];
		if (edge) {
			this._updateEdge(line, edge);
		} else {
			// Remove the line.
			this._edgeContainer.removeChild(line);
		}
	}
};

JGraph2Proto._ensureEnoughEdges = function(number) {
	const elementsNeeded = number - this._edgeContainer.childNodes.length;
	if (elementsNeeded > 0) {
		for (let i = 0; i < elementsNeeded; i++) {
			this._createEdge();
		}
	}
};

JGraph2Proto._createEdge = function() {
	const newElement = document.createElement('line');
	newElement.setAttribute('stroke', 'red');
	this._edgeContainer.appendChild(newElement);
};

JGraph2Proto._updateEdge = function(line, edge) {
	let from;
	let to;
	if (edge instanceof Array) {
		from = edge[0];
		to = edge[1];

	} else if (edge.nodes instanceof Array) {
		from = edge.nodes[0];
		to = edge.nodes[1];
	}
	line.id = `edge_${from.id}_${to.id}`;
	from = document.getElementById(from.id);
	to = document.getElementById(to.id);
	if (from && to) {
		line.setAttribute('x1', from.getAttribute('cx'));
		line.setAttribute('y1', from.getAttribute('cy'));
		line.setAttribute('x2', to.getAttribute('cx'));
		line.setAttribute('y2', to.getAttribute('cy'));
	}
	if (edge.colour) {
		line.setAttribute('data-colour', edge.colour);
	}
};

/**
 * A JGraph is an element that listens to updates on a STOMP destination and manages nodes and edges on a graph
 */
var JGraph2 = document.registerElement('j-graph2', {
	prototype: JGraph2Proto
});