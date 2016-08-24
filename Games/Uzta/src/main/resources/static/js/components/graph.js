var JGraphProto = Object.create(HTMLElement.prototype);

JGraphProto.attachedCallback = function() {

	var template = document.querySelector('#graph-template');
	var clone = document.importNode(template.content, true);

	this._nodeContainer = clone.querySelector('.nodes');
	this._edgeContainer = clone.querySelector('.edges');

	this.appendChild(clone);

	this._nodeElementName = this.getAttribute('nodeElement');
	this._edgeElementName = this.getAttribute('edgeElement') || 'JLine';
	var destination = this.getAttribute('destination');

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

	var initialData = JSON.parse(this.getAttribute('data-initial'));
	this._redrawGraph(initialData && initialData.payload);

	socket.subscribe(destination, this._redrawGraph.bind(this));
};

/**
 * @param {{nodes: Array.<{id: string, x: number, y: number}>, edges: Array.<Array>}} graph
 * @private
 */
JGraphProto._redrawGraph = function(graph) {
	if (!graph) {
		return;
	}
	var nodes = graph.nodes;
	this._updateNodes(nodes);

	var edges = graph.edges;
	this._updateEdges(edges);
};

JGraphProto._updateNodes = function(nodes) {
	this._ensureEnoughNodes(nodes.length);
	for (var i = this._nodeContainer.childNodes.length - 1; i >= 0; i--) {
		var element = this._nodeContainer.childNodes[i];
		var node = nodes[i];
		if (node) {
			this._updateNode(element, node);
		} else {
			// Remove the element.
			this._nodeContainer.removeChild(element);
		}
	}
};

JGraphProto._ensureEnoughNodes = function(number) {
	var elementsNeeded = number - this._nodeContainer.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._createNode();
		}
	}
};

JGraphProto._createNode = function() {
	var newElement = createElement(this._nodeElementName, this._defaultNodeAttributes);
	this._nodeContainer.appendChild(newElement);
};

JGraphProto._updateNode = function(element, node) {
	element.id = node.id;
	element.style.left = node.x + '%';
	element.style.bottom = node.y + '%';
	element.className = node.type.toLowerCase();
	if (node.highlighted) {
		element.classList.add('highlighted')
	}
	
	element.setAttribute('data-node', JSON.stringify(node));
};

JGraphProto._updateEdges = function(edges) {
	this._ensureEnoughEdges(edges.length);
	for (var i = this._edgeContainer.childNodes.length - 1; i >= 0; i--) {
		var line = this._edgeContainer.childNodes[i];
		var edge = edges[i];
		if (edge) {
			this._updateEdge(line, edge);
		} else {
			// Remove the line.
			this._edgeContainer.removeChild(line);
		}
	}
};

JGraphProto._ensureEnoughEdges = function(number) {
	var elementsNeeded = number - this._edgeContainer.childNodes.length;
	if (elementsNeeded > 0) {
		for (var i = 0; i < elementsNeeded; i++) {
			this._createEdge();
		}
	}
};

JGraphProto._createEdge = function() {
	var newElement = createElement(this._edgeElementName, this._defaultEdgeAttributes);
	this._edgeContainer.appendChild(newElement);
};

JGraphProto._updateEdge = function(line, edge) {
	if (edge instanceof Array) {
		line.setEnds(edge[0], edge[1]);

	} else if (edge.nodes instanceof Array) {
		line.setEnds(edge.nodes[0], edge.nodes[1]);
		line.setAttribute('data-colour', edge.colour);
	}
};

/**
 * A JGraph is an element that listens to updates on a STOMP destination and manages nodes and edges on a graph
 */
var JGraph = document.registerElement('j-graph', {
	prototype: JGraphProto
});