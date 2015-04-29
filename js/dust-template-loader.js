function DustTemplateLoader(load) {
    var templatesCache = {};

    this.load = function(templateName) {
        if (templatesCache[templateName] == null) {
            var filePath = 'js/templates/' + templateName + '.dust';

            templatesCache[templateName] = load(filePath);
        }

        return templatesCache[templateName];
    }
}
