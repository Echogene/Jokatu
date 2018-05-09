/**
 * A JGraph is an element that listens to updates on a STOMP destination and manages nodes and edges on a graph
 */
class JGraph extends HTMLElement {
	connectedCallback() {
		if (!this._alreadySetUp) {
			const template = document.querySelector('#graph-template');
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
		}
		this._alreadySetUp = true;
	}

	/**
	 * @param {{nodes: Array.<{id: string, x: number, y: number}>, edges: Array.<Array>}} graph
	 * @private
	 */
	_redrawGraph(graph) {
		if (!graph) {
			return;
		}
		const nodes = graph.nodes;
		this._updateNodes(nodes);

		const edges = graph.edges;
		this._updateEdges(edges);
	};

	_updateNodes(nodes) {
		this._ensureEnoughNodes(nodes.length);
		for (let i = this._nodeContainer.childNodes.length - 1; i >= 0; i--) {
			const element = this._nodeContainer.childNodes[i];
			const node = nodes[i];
			if (node) {
				JGraph._updateNode(element, node);
			} else {
				// Remove the element.
				this._nodeContainer.removeChild(element);
			}
		}
	};

	_ensureEnoughNodes(number) {
		const elementsNeeded = number - this._nodeContainer.childNodes.length;
		if (elementsNeeded > 0) {
			for (let i = 0; i < elementsNeeded; i++) {
				this._createNode();
			}
		}
	};

	_createNode() {
		const newElement = createElement(this._nodeElementName, this._defaultNodeAttributes);
		this._nodeContainer.appendChild(newElement);
	};

	static _updateNode(element, node) {
		element.id = node.id;
		element.style.left = node.x + '%';
		element.style.bottom = node.y + '%';
		element.className = node.type.toLowerCase();
		if (node.highlighted) {
			element.classList.add('highlighted')
		}

		element.setAttribute('data-node', JSON.stringify(node));
	};

	_updateEdges(edges) {
		this._ensureEnoughEdges(edges.length);
		for (let i = this._edgeContainer.childNodes.length - 1; i >= 0; i--) {
			const line = this._edgeContainer.childNodes[i];
			const edge = edges[i];
			if (edge) {
				JGraph._updateEdge(line, edge);
			} else {
				// Remove the line.
				this._edgeContainer.removeChild(line);
			}
		}
	};

	_ensureEnoughEdges(number) {
		const elementsNeeded = number - this._edgeContainer.childNodes.length;
		if (elementsNeeded > 0) {
			for (let i = 0; i < elementsNeeded; i++) {
				this._createEdge();
			}
		}
	};

	_createEdge() {
		const newElement = createElement(this._edgeElementName, this._defaultEdgeAttributes);
		this._edgeContainer.appendChild(newElement);
	};

	static _updateEdge(line, edge) {
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
		if (from && to) {
			line.setEnds(from, to);
		}
		if (edge.colour) {
			line.setAttribute('data-colour', edge.colour);
		}
	};
}

customElements.define('j-graph', JGraph);