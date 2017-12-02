package fzi.webtest.pages.artificial


class AtAlwaysFalsePageChildWorkaround extends AtAlwaysFalsePage {

    static url = '/'

    static at = { Object.at && true }
}
